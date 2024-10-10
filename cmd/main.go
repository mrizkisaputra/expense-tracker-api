package main

import (
	"expense-tracker-api/config"
	"fmt"
	"github.com/joho/godotenv"
	"os"
)

func main() {
	if err := godotenv.Load(); err != nil {
		panic("Error loading .env file")
	}

	Log := config.NewLogger()
	App := config.NewApp()
	DB := config.NewDatabase(Log)
	Validate := config.NewValidation()

	config.NewBootstrap(&config.BootstrapConfig{
		App:      App,
		Log:      Log,
		DB:       DB,
		Validate: Validate,
	})

	panic(App.Listen(fmt.Sprintf(":%s", os.Getenv("SERVER.PORT"))))
}
