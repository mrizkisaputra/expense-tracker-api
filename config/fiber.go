package config

import (
	"errors"
	api_response "expense-tracker-api/pkg/api-response"
	"github.com/go-playground/validator/v10"
	"github.com/gofiber/fiber/v2"
	"os"
	"time"
)

func NewApp() *fiber.App {
	return fiber.New(fiber.Config{
		AppName:      os.Getenv("APP.NAME"),
		ErrorHandler: ErrorHandle,
	})
}

func ErrorHandle(ctx *fiber.Ctx, err error) error {
	statusCode := fiber.StatusInternalServerError

	var e *fiber.Error
	if errors.As(err, &e) {
		return ctx.Status(e.Code).JSON(api_response.ApiResponseError{
			Status:    e.Code,
			Timestamp: time.Now().Format("2006-01-02 15:04:05"),
			Message:   e.Message,
			Errors:    nil,
		})
	}

	var errValidation validator.ValidationErrors
	if errors.Is(err, errValidation) {
		return ctx.Status(e.Code).JSON(api_response.ApiResponseError{
			Status:    e.Code,
			Timestamp: time.Now().Format("2006-01-02 15:04:05"),
			Message:   e.Message,
			Errors:    validatedError(errValidation),
		})
	}

	return ctx.Status(statusCode).JSON(nil)
}

func validatedError(err validator.ValidationErrors) []api_response.ApiValidationError {
	var apiValidationErr []api_response.ApiValidationError
	fieldTagMessage := map[string]map[string]string{
		"Name": {
			"required": "REQUIRED",
			"max":      "TO_LONG",
		},
		"Email": {
			"required": "REQUIRED",
			"email":    "EMAIL_FORMAT",
			"max":      "TO_LONG",
		},
		"Password": {
			"required": "REQUIRED",
			"min":      "TO_SHORT",
			"max":      "TO_LONG",
		},
	}
	for _, e := range err {
		if msg, ok := fieldTagMessage[e.Field()][e.Tag()]; ok {
			apiValidationErr = append(apiValidationErr, api_response.ApiValidationError{
				Field:         e.Field(),
				RejectedValue: e.Value(),
				Message:       msg,
			})
		}
	}

	return apiValidationErr
}
