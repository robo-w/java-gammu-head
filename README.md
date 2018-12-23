# JSON Head for Gammu

[Gammu] is an open source tool to control GSM devices.
This Java head provides an easy way to send text messages via a JSON interface.

## Used Technologies

* [Gammu] with SMSD needs to be installed on the machine where the Java Head is running
* [Spark] is used to provide the the standalone webserver of the program

[Gammu]: https://de.wammu.eu/gammu/
[Spark]: http://sparkjava.com/

## JSON API

### Status Overview

* Endpoint: `/`
* HTTP Method: `GET`

Returns the current status of the java-head.
Currently the number of queued messages is shown.

### Send Text Message "SMS"

* Endpoint: `/sms/send`
* HTTP Method: `POST`

Message payload:

```json
{
	"authenticationToken": "Some Authentication Token",
	"targetPhoneNumbers": [
		  "+43678123456"
		],
	"messageContent": "This is a test message."
}
```

The authentication token can be configured on startup of the server. (Not implemented yet)

## Required Hardware

Any hardware supported by Gammu can be used to send text messages.
To check if Gammu SMSD is correctly installed, following command can be used:
`gammu-smsd-inject TEXT "+4367812345678" -text "This is a test message."`

## Build

* Required tools for building: Git, Maven, JDK: `sudo apt-get install git maven openjdk-8-jdk`
* Checkout Git repository: `git checkout https://github.com/robo-w/java-gammu-head.git`
* (Optional) Bind `InternationalPhoneNumberValidator` instead of `AustrianPhoneNumberValidator` in `ConfigurationGuiceModule`.
  * It is also possible to implement a custom, localized validator, to restrict SMS sending to foreign countries.
* Build JAR package: `mvn clean package`, executed in the created folder by git checkout.
* Resulting JAR package with `-jar-with-dependencies.jar` suffix can be started by `java -jar java-gammu-head-1.0.0-jar-with-dependencies.jar`or be copied to another machine, e.g. a Raspberry Pi.

## Installation

Currently it is only tested on a Raspberry Pi.
On Debian or Ubuntu based systems it should work out of the box.
For Windows some code adaptions are needed (e.g. the name of the executable of gammu).

* Install gammu-smsd: `sudo apt-get install gammu-smsd`
* Update the Gammu SMSD configuration. [See Gammu documentation for details.](https://wammu.eu/docs/manual/smsd/config.html)
* Make sure that the serial device is accessible by the user which runs the java program
  * On Raspbian you should add the pi user to the groups "gammu", "tty" and "dialout" by `sudo usermod -a -G dialout,gammu,tty pi`. Do not forget to re-login pi to apply the new user groups.
  * Some of the common Huawei data sticks change their serial port numbering during operation. You can prevent broken links by using the exact device name by id. For example `/dev/serial/by-id/usb-HUAWEI_Technology_HUAWEI_Mobile-if00-port0`
* Install java: `sudo apt-get install openjdk-8-jre`
* Start java-gammu head: `java -jar java-gammu-head-1.0.0.jar`
* The JSON endpoint is now available on port `8080`.

