FROM nginx:latest
EXPOSE 80
COPY ./fsuvius/static /var/www/static
RUN rm -f /etc/nginx/conf.d/default.conf
COPY ./docker/nginx.conf /etc/nginx/conf.d/default.conf
