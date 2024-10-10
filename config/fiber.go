package config

import (
	"github.com/gofiber/fiber/v2"
	"os"
)

func NewApp() *fiber.App {
	return fiber.New(fiber.Config{
		AppName:      os.Getenv("APP.NAME"),
		ErrorHandler: ErrorHandle,
	})
}

func ErrorHandle(ctx *fiber.Ctx, err error) error {
	status := fiber.StatusInternalServerError
	return ctx.Status(status).JSON(nil)
}
