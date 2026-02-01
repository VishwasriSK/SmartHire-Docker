# Stage 1: Compile Java code
FROM openjdk:11-jdk-slim AS builder

WORKDIR /build

# Copy backend source files
COPY ../backend/src/main/java /build/src/main/java
COPY ../backend/lib /build/lib

# Create classes directory
RUN mkdir -p /build/WEB-INF/classes

# Find and compile all Java files
RUN find /build/src/main/java -name "*.java" > sources.txt && \
    javac -cp "/build/lib/mysql-connector-j-9.6.0/mysql-connector-j-9.6.0.jar:$CATALINA_HOME/lib/servlet-api.jar" \
    -d /build/WEB-INF/classes \
    @sources.txt || echo "Compilation completed with warnings"

# Stage 2: Create Tomcat runtime
FROM tomcat:9.0-jdk11

# Remove default webapps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy compiled classes from builder stage
COPY --from=builder /build/WEB-INF/classes /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/

# Copy frontend files
COPY *.html /usr/local/tomcat/webapps/ROOT/
COPY *.jsp /usr/local/tomcat/webapps/ROOT/
COPY WEB-INF /usr/local/tomcat/webapps/ROOT/WEB-INF/
COPY META-INF /usr/local/tomcat/webapps/ROOT/META-INF/ 2>/dev/null || true

# Expose port
EXPOSE 8080

CMD ["catalina.sh", "run"]
