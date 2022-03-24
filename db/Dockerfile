FROM mysql

ENV MYSQL_DATABASE 'Delegacia'
ENV MYSQL_ROOT_PASSWORD 'root'

WORKDIR /sql

COPY create_db.sql ./

EXPOSE 3306
