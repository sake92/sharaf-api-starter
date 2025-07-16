# sharaf-api-starter




```shell
# creates db and populates it with some data
./mill -i api.flywayMigrate

# generate db boilerplate source code
./mill -i api.squeryGenerate


# generate api boilerplate source code
./mill -i api.openApi4sGenerate


# format source code
./mill -i mill.scalalib.scalafmt/


```