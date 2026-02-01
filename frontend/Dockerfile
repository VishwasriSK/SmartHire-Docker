FROM tomcat:9.0-jdk11

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy all frontend files to Tomcat webapps
COPY . /usr/local/tomcat/webapps/ROOT/

# Expose port
EXPOSE 8080

CMD ["catalina.sh", "run"]
