package usecase

import (
	"context"
	"expense-tracker-api/internal/auth-service/models"
	"expense-tracker-api/internal/auth-service/repositories"
	"github.com/go-playground/validator/v10"
	"github.com/gofiber/fiber/v2"
	"github.com/golang-jwt/jwt/v5"
	"github.com/google/uuid"
	"github.com/sirupsen/logrus"
	"golang.org/x/crypto/bcrypt"
	"gorm.io/gorm"
	"net/http"
	"os"
	"time"
)

type userUseCase struct {
	Repository repositories.UserRepositoryInterface
	Log        *logrus.Logger
	Validate   *validator.Validate
	DB         *gorm.DB
}

func NewUserUseCase(
	repo repositories.UserRepositoryInterface,
	log *logrus.Logger,
	validate *validator.Validate,
	DB *gorm.DB,
) UserUseCaseInterface {
	return &userUseCase{
		Repository: repo,
		Log:        log,
		Validate:   validate,
		DB:         DB,
	}
}

func (u *userUseCase) Register(ctx context.Context, request *models.RegisterRequest) (models.RegisterResponse, error) {
	tx := u.DB.WithContext(ctx).Begin()
	defer tx.Rollback()

	if err := u.Validate.Struct(request); err != nil {
		u.Log.WithError(err).Error("error validated register request body")
		return models.RegisterResponse{}, err.(validator.ValidationErrors)
	}

	total, err := u.Repository.CountByEmail(tx, request.Email)
	if err != nil {
		u.Log.WithError(err).Error("error count user by email")
		return models.RegisterResponse{}, fiber.ErrInternalServerError
	}

	if total > 0 {
		u.Log.Info("user with email already exists")
		return models.RegisterResponse{}, fiber.NewError(http.StatusConflict, "email already exists")
	}

	encryptPass, err := bcrypt.GenerateFromPassword([]byte(request.Password), bcrypt.DefaultCost)
	entity := models.User{
		Id:       uuid.New().String(),
		Name:     request.Name,
		Email:    request.Email,
		Password: string(encryptPass),
	}
	user, err := u.Repository.Create(tx, &entity)
	if err != nil {
		u.Log.WithError(err).Error("error create user")
		return models.RegisterResponse{}, fiber.ErrInternalServerError
	}

	if err := tx.Commit().Error; err != nil {
		u.Log.WithError(err).Error("error commit user register transaction")
		return models.RegisterResponse{}, fiber.ErrInternalServerError
	}

	return u.toRegisterResponse(&user), nil
}

func (u *userUseCase) toRegisterResponse(user *models.User) models.RegisterResponse {
	return models.RegisterResponse{
		Id:        user.Id,
		Name:      user.Name,
		Email:     user.Email,
		Password:  user.Password,
		CreatedAt: user.CreatedAt,
	}
}

func (u *userUseCase) Login(ctx context.Context, request *models.LoginRequest) (models.TokenUserLoginResponse, error) {
	tx := u.DB.WithContext(ctx).Begin()
	defer tx.Rollback()

	if err := u.Validate.Struct(request); err != nil {
		u.Log.WithError(err).Error("error validated login request body")
		return models.TokenUserLoginResponse{}, err.(validator.ValidationErrors)
	}

	user, err := u.Repository.FindByEmail(tx, request)
	if err != nil {
		u.Log.WithError(err).Error("error find by email")
		return models.TokenUserLoginResponse{}, fiber.NewError(fiber.StatusUnauthorized, "invalid email")
	}

	if err := bcrypt.CompareHashAndPassword([]byte(user.Password), []byte(request.Password)); err != nil {
		u.Log.WithError(err).Error("error compare password")
		return models.TokenUserLoginResponse{}, fiber.NewError(fiber.StatusUnauthorized, "invalid password")
	}

	// create JWT token
	claims := models.JWTClaims{
		Id:    user.Id,
		Email: user.Email,
		RegisteredClaims: jwt.RegisteredClaims{
			Issuer: os.Getenv("APP.NAME"),
			ExpiresAt: &jwt.NumericDate{
				Time: time.Now().Add(time.Second * 1800),
			},
		},
	}
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	tokenString, errSigned := token.SignedString([]byte(os.Getenv("JWT.SECREAT_KEY")))
	if errSigned != nil {
		u.Log.WithError(err).Error("error sign token")
		return models.TokenUserLoginResponse{}, fiber.ErrInternalServerError
	}

	if err := tx.Commit().Error; err != nil {
		u.Log.WithError(err).Error("error commit user login transaction")
		return models.TokenUserLoginResponse{}, fiber.ErrInternalServerError
	}

	return models.TokenUserLoginResponse{
		JwtToken: tokenString,
	}, nil
}
