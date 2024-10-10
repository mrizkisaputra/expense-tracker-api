package config

import (
	"github.com/sirupsen/logrus"
	"os"
	"strconv"
)

func NewLogger() *logrus.Logger {
	log := logrus.New()
	log.SetFormatter(&logrus.JSONFormatter{
		TimestampFormat: "Jan 02-2006 15:04:05",
	})
	level, err := strconv.ParseInt(os.Getenv("LOG.LEVEL"), 10, 64)
	if err != nil {
		log.WithError(err).Warning("cannot parse LOG.LEVEL")
	}
	log.SetLevel(logrus.Level(level))

	return log
}
