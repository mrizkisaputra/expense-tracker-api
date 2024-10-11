package usecase

import (
	"context"
	"expense-tracker-api/internal/auth-service/models"
)

type UserUseCaseInterface interface {
	Register(ctx context.Context, request *models.RegisterRequest) (models.RegisterResponse, error)

	Login(ctx context.Context, request *models.LoginRequest) (models.TokenUserLoginResponse, error)
}
