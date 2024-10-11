package config

import (
	"expense-tracker-api/internal/auth-service/delivery"
	"expense-tracker-api/internal/auth-service/repositories"
	"expense-tracker-api/internal/auth-service/usecase"
	"github.com/go-playground/validator/v10"
	"github.com/gofiber/fiber/v2"
	"github.com/sirupsen/logrus"
	"gorm.io/gorm"
)

type BootstrapConfig Bootstrap
type Bootstrap struct {
	App      *fiber.App
	Log      *logrus.Logger
	DB       *gorm.DB
	Validate *validator.Validate
}

func NewBootstrap(config *BootstrapConfig) {
	// setup repositories
	userRepo := repositories.NewUserRepository()

	// setup usecase
	userUseCase := usecase.NewUserUseCase(userRepo, config.Log, config.Validate, config.DB)

	// setup controllers
	userController := delivery.NewUserController(userUseCase)

	// setup midlleware

	//setup routes
	authRoute := delivery.AuthRoute{
		App:            config.App,
		UserController: userController,
	}
	authRoute.SetupRoutes()
}
