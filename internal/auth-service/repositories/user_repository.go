package repositories

import (
	"expense-tracker-api/internal/auth-service/models"
	"gorm.io/gorm"
)

type userRepository struct {
}

func NewUserRepository() UserRepositoryInterface {
	return &userRepository{}
}

func (u *userRepository) Create(db *gorm.DB, entity *models.User) (models.User, error) {
	// INSERT INTO users VALUES(id,name,email,password,created_at)
	if err := db.Create(entity).Error; err != nil {
		return models.User{}, err
	}
	return *entity, nil
}

func (u *userRepository) CountByEmail(db *gorm.DB, email string) (int64, error) {
	//SELECT count(*) FROM users WHERE email = email
	var total int64
	if err := db.Model(models.User{}).Where(models.User{Email: email}).Count(&total).Error; err != nil {
		return 0, err
	}
	return total, nil
}

func (u *userRepository) FindByEmail(db *gorm.DB, request *models.LoginRequest) (models.User, error) {
	var user models.User
	if err := db.Select("id", "email", "password").
		Where(models.User{Email: request.Email}).Take(&user).Error; err != nil {
		return models.User{}, err
	}
	return user, nil
}
