package delivery

import (
	"expense-tracker-api/internal/auth-service/models"
	"expense-tracker-api/internal/auth-service/usecase"
	api_response "expense-tracker-api/pkg/api-response"
	"github.com/gofiber/fiber/v2"
	"time"
)

type UserController struct {
	useCase usecase.UserUseCaseInterface
}

func NewUserController(useCase usecase.UserUseCaseInterface) UserController {
	return UserController{
		useCase: useCase,
	}
}

func (u UserController) Register(ctx *fiber.Ctx) error {
	var request models.RegisterRequest
	if err := ctx.BodyParser(&request); err != nil {
		return fiber.NewError(fiber.StatusBadRequest, "invalid request body")
	}

	response, err := u.useCase.Register(ctx.UserContext(), &request)
	if err != nil {
		return err
	}

	apiResponse := api_response.ApiResponse{
		Status:    fiber.StatusCreated,
		Timestamp: time.Now().Format("2006-01-02 15:04:05"),
		Message:   "User created",
		Data:      response,
	}

	return ctx.Status(fiber.StatusCreated).JSON(apiResponse, "application/json")
}

func (u UserController) Login(ctx *fiber.Ctx) error {
	var request models.LoginRequest
	if err := ctx.BodyParser(&request); err != nil {
		return fiber.NewError(fiber.StatusBadRequest, "invalid request body")
	}

	response, err := u.useCase.Login(ctx.UserContext(), &request)
	if err != nil {
		return err
	}

	apiResponse := api_response.ApiResponse{
		Status:    fiber.StatusOK,
		Timestamp: time.Now().Format("2006-01-02 15:04:05"),
		Message:   "Login success",
		Data:      response,
	}

	return ctx.Status(fiber.StatusOK).JSON(apiResponse, "application/json")
}
