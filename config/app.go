package config

import (
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

	// setup usecase

	// setup midlleware

	//setup routes
}
