from django.db import models
from django.utils import timezone
from django.conf import settings

class User(models.Model):
    name = models.CharField(
        verbose_name="Name",
        max_length=64,
        unique=True,
        blank=False,
    )

    balance = models.FloatField(
        verbose_name="Balance",
        default=0.0,
    )

    pfp = models.FileField(
        verbose_name="Profile Photo",
        upload_to="pfps/%Y/%m/%d/",
        null=True,
    )

    def create():
        new_user = User.objects.filter(name="New User").first()
        if new_user is None:
            new_user = User.objects.create(name="New User")
        return new_user

    def credit(self):
        self.set_balance(self.balance + 1.0)

    def debit(self):
        self.set_balance(self.balance - 1.0)

    def set_balance(self, balance: float):
        if not(balance >= 0.0 or balance <= 0.0):
            raise AssertionError("New balance must be a number.")
        Transaction.log(self, balance - self.balance)
        self.balance = balance

    def set_name(self, name: str):
        if User.objects.filter(name=name).exists():
            raise AssertionError("A user with this name already exists.")
        self.name = name


class Transaction(models.Model):
    user = models.ForeignKey(
        User,
        on_delete=models.CASCADE,
        verbose_name="User",
    )

    amount = models.FloatField(
        verbose_name="Amount",
    )

    timestamp = models.DateTimeField(
        verbose_name="Date/Time",
        default=timezone.now,
    )

    def __prune():
        cutoff = timezone.now() - timezone.timedelta(days=settings.TRANSACTION_HISTORY_DAYS)
        Transaction.objects.filter(timestamp__lt=cutoff).delete()

    def log(user: User, amount: float):
        Transaction.__prune()
        Transaction.objects.create(user=user, amount=amount)
