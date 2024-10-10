package config

import (
	"fmt"
	"github.com/sirupsen/logrus"
	"gorm.io/driver/mysql"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
	"os"
	"time"
)

func NewDatabase(Log *logrus.Logger) *gorm.DB {
	dsn := fmt.Sprintf("%s:%s@tcp(%s:%s)/%s?charset=utf8mb4&parseTime=True&loc=Local",
		os.Getenv("DB.USER"),
		os.Getenv("DB.PASSWORD"),
		os.Getenv("DB.HOST"),
		os.Getenv("DB.PORT"),
		os.Getenv("DB.NAME"))

	db, errOpenConn := gorm.Open(mysql.Open(dsn), &gorm.Config{
		Logger:                 logger.Default.LogMode(logger.Info),
		SkipDefaultTransaction: true,
	})

	if errOpenConn != nil {
		Log.WithError(errOpenConn).Panic("Failed to connect to database")
	}

	sqlDB, err := db.DB()
	if err != nil {
		Log.WithError(errOpenConn).Panic("Failed to connect to database")
	}

	// SetMaxIdleConns sets the maximum number of connections in the idle connection pool.
	sqlDB.SetMaxIdleConns(10)

	// SetMaxOpenConns sets the maximum number of open connections to the database.
	sqlDB.SetMaxOpenConns(100)

	// SetConnMaxLifetime sets the maximum amount of time a connection may be reused.
	sqlDB.SetConnMaxLifetime(time.Hour)

	return db
}
