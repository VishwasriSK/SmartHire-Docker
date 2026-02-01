FROM tomcat:9.0-jdk11

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy all backend files to Tomcat webapps
COPY . /usr/local/tomcat/webapps/SmartHire/

# Expose port
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]
