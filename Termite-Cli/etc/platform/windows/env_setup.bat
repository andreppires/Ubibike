@echo off

::
:: Absolute path to the Termite CLI tool
::
setx TERMITE_CLI_PATH "XXX" /m

::
:: Target platform; one of: mac, linux, or windows
::
setx TERMITE_PLATFORM windows /m
