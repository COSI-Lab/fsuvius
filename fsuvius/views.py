import logging

from django.conf import settings
from django.contrib import messages
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from django.core.exceptions import PermissionDenied
from django.db import transaction
from django.http import HttpRequest, HttpResponse
from django.shortcuts import redirect, render, get_object_or_404
from django.views.decorators.http import require_GET, require_http_methods, require_POST

from fsuvius.models import User, Transaction
from fsuvius.forms import UserForm

_logger = logging.getLogger("fsuvius")

@require_GET
def index(request: HttpRequest):
    context = {
        "users": User.objects.all().order_by("name")
    }
    return render(request, "fsuvius/index.html", context)

@require_POST
def credit(request: HttpRequest, user_id: int):
    user = get_object_or_404(User, id=user_id)
    user.credit()
    user.save()
    _logger.info("Credit 1 FSU to user \"%s\".", user.name)
    return HttpResponse(user.balance)

@require_POST
def debit(request: HttpRequest, user_id: int):
    user = get_object_or_404(User, id=user_id)
    user.debit()
    user.save()
    _logger.info("Debit 1 FSU from user \"%s\".", user.name)
    return HttpResponse(user.balance)

@require_http_methods(["GET", "POST"])
def create_user(request: HttpRequest):
    user = User.create()
    return redirect("edit_user", user_id=user.id)

@require_http_methods(["GET", "POST"])
def edit_user(request: HttpRequest, user_id: int):
    user = get_object_or_404(User, id=user_id)
    if request.method == "GET":
        form = UserForm()
    else:
        form = UserForm(request.POST)
    context = {
        "user": user,
        "form": form,
    }
    return render(request, "fsuvius/user/edit.html", context)

@require_http_methods(["GET", "POST"])
def delete_user(request: HttpRequest, user_id: int):
    user = get_object_or_404(User, id=user_id)
    match request.method:
        case "GET":
            pass
        case "POST":
            pass
