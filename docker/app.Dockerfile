FROM python:3.12-bookworm
EXPOSE 8000
RUN pip install django gunicorn
RUN useradd --user-group --create-home fsu
USER fsu
WORKDIR /home/fsu
COPY --chown=fsu:fsu ./fsuvius ./fsuvius
RUN rm -f ./fsuvius/settings.py
COPY --chown=fsu:fsu ./docker/settings.py ./fsuvius/settings.py
COPY --chown=fsu:fsu ./manage.py .
STOPSIGNAL SIGINT
CMD ["gunicorn", "-w 4", "--threads=4", "-t 15", "-b 0.0.0.0:8000", "fsuvius.wsgi"]
