#   Not used

#FROM tomcat:8.0.51-jre8-alpine
#
#RUN rm -rf /usr/local/tomcat/webapps/*
#
#COPY ./target/ecommerce-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war
#
#RUN mkdir /usr/local/tomcat/images
#
#COPY ./images/* /usr/local/tomcat/images/
#
#CMD ["catalina.sh","run"]
