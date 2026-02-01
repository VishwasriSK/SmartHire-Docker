FROM mysql:8.0

# Copy initialization scripts (if you have any SQL files)
COPY *.sql /docker-entrypoint-initdb.d/

# Expose MySQL port
EXPOSE 3306
