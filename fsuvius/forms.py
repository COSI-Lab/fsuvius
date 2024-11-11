from django import forms

class UserForm(forms.Form):
    name = forms.CharField(
        label="Name",
        max_length=64,
        required=True,
        strip=True,
    )

    balance = forms.FloatField(
        label="Balance",
        required=True,
    )

    pfp = forms.FileField(
        label="Profile Photo",
    )
