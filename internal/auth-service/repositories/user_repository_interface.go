package repositories

import (
	"expense-tracker-api/internal/auth-service/models"
	"gorm.io/gorm"
)

type UserRepositoryInterface interface {
	Create(db *gorm.DB, entity *models.User) (models.User, error)

	CountByEmail(db *gorm.DB, email string) (int64, error)

	FindByEmail(db *gorm.DB, request *models.LoginRequest) (models.User, error)
}
