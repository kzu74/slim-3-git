
function fokus() {
    document.getElementById("fokus").focus();
}

function sendForm() {
    document.forms[0].submit();
}

function sendFormAButton(buttonValue) {
    document.getElementById("buttonAct").value = buttonValue;
    document.forms[0].submit();
}

function redir(relativeURL) {
    window.location.href = relativeURL;
}



function goBack() {
    window.history.go(-1);
}

function scrollToH1() {
    $('html, body').animate({
        scrollTop: $("h1").offset().top
    }, 1000);
}
