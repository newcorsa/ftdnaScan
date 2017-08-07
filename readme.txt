HOW TO LOAD ojdbc6.jar INTO LOCAL REPO:

mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0.3 -Dpackaging=jar -Dfile=./ojdbc6-11.2.0.3.jar -DgeneratePom=true