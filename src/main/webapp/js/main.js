$(document).ready(main);

//INITIALIZATION
function main(){

    //show RegForm
    $("#regButton").click(function(){
        $("#regForm").toggle();
    });
    //Check if Logged in
    checkForLoggedIn();

    //On Country Select
    $("#select_country").change(function(){
        clearMessages();
        updateContentCity();
    });

    //delete country
    $("#delete_country").click(function(){
        clearMessages();
        country = $("#select_country>option:selected").text();
        deleteCountry(country);
    });

    //delete city
    $("#delete_city").click(function(){
        clearMessages();
        country = getSelectedCountry();
        city = getSelectedCity();
        deleteCity(country, city);
    })

    //update country
    $("#update_country").click(function(){
        clearMessages();
        country = getSelectedCountry();
        newCountry = $("#new_country_name").val();
        updateCountry(country, newCountry);
    })

    //update city
    $("#update_city").click(function(){
        clearMessages();
        country = getSelectedCountry();
        city = getSelectedCity();
        newCity = $("#new_city_name").val();
        updateCity(country, city, newCity);
    })

    //add city
    $("#add_city").click(function(){
        clearMessages();
        country = getSelectedCountry();
        newCity = $("#new_city_name").val();
        addCity(country, newCity);
    })

    //add country
    $("#add_country").click(function(){
        clearMessages();
        newCountry = $("#new_country_name").val();
        addCountry(newCountry);
    })
}

//FUNCTIONS
function updateContentCity(){
    country = getSelectedCountry();
    $.ajax({
        url: "/api/country/"+country+"/city",
        type: "GET",
        success: function(data) {
            if(data.status == "Success") {

                clearCities();

                for(i = 0; i < data.result.length; i++) {
                    appendCity(data.result[i]);
                }

            } else {
                $("#requestError").text(data.result);
            }
        },
        error: function() {
            $("#requestError").text("Invalid Request");
        }
    });
}

function updateContentCountry() {
    $.ajax({
        url: "/api/country/",
        type: "GET",
        success: function(data) {
            if(data.status == "Success") {
                clearCountries();

                for(i = 0; i < data.result.length; i++) {
                    appendCountry(data.result[i]);
                }

                updateContentCity();

            } else {
                $("#requestError").text(data.result);
            }
        },
        error: function() {
            $("#requestError").text("Invalid Request");
        }
    });
}

function appendCountry(country) {
    $('#select_country').append($('<option>', {
        value: country,
        text: country
    }));
}

function appendCity(city) {
    $('#select_city').append($('<option>', {
        value: city,
        text: city
    }));
}

function clearCountries() {
    $("#select_country option").remove();
}

function clearCities() {
    $("#select_city option").remove();
}

function clearMessages(){
    $("#loginError").text("");
    $("#requestError").text("");
    $("#requestSuccess").text("");
    $("#regError").text("");
}

function getSelectedCity() {
    return $("#select_city>option:selected").text();
}

function getSelectedCountry() {
    return $("#select_country>option:selected").text();
}

function addCountry(newCountry) {
    $.ajax({
        url: "/api/country",
        type: "POST",
        data: "country="+newCountry,
        success: function(data) {
            if(data.status == "Success") {
                $("#requestSuccess").text(data.result);
                updateContentCountry();
            } else {
                $("#requestError").text(data.result);
            }
        },
        error: function() {
            $("#requestError").text("Invalid Request");
        }
    });
}

function addCity(country, newCity) {
    $.ajax({
        url: "/api/country/"+country+"/city",
        type: "POST",
        data: "city="+newCity,
        success: function(data) {
            if(data.status == "Success") {
                $("#requestSuccess").text(data.result);
                updateContentCountry();
            } else {
                $("#requestError").text(data.result);
            }
        },
        error: function() {
            $("#requestError").text("Invalid Request");
        }
    });
}

function updateCity(country, city, newCity){
    $.ajax({
        url: "/api/country/"+country+"/city/"+city,
        type: "PUT",
        data: "new_city_name="+newCity,
        success: function(data) {
            if(data.status == "Success") {
                $("#requestSuccess").text(data.result);
                updateContentCountry();
            } else {
                $("#requestError").text(data.result);
            }
        },
        error: function() {
            $("#requestError").text("Invalid Request");
        }
    });
}

function updateCountry(country, newCountry){
    $.ajax({
        url: "/api/country/"+country,
        type: "PUT",
        data: "new_country_name="+newCountry,
        success: function(data) {
            if(data.status == "Success") {
                $("#requestSuccess").text(data.result);
                updateContentCountry();
            } else {
                $("#requestError").text(data.result);
            }
        },
        error: function() {
            $("#requestError").text("Invalid Request");
        }
    });
}

function deleteCountry(country) {
    $.ajax({
        url: "/api/country/"+country,
        type: "DELETE",
        success: function(data) {
            if(data.status == "Success") {
                $("#requestSuccess").text(data.result);
                updateContentCountry();
            } else {
                $("#requestError").text(data.result);
            }
        },
        error: function() {
            $("#requestError").text("Invalid Request");
        }
    });
}

function deleteCity(country, city) {
    $.ajax({
        url: "/api/country/"+country+"/city/"+city,
        type: "DELETE",
        success: function(data) {
            if(data.status == "Success") {
                $("#requestSuccess").text(data.result);
                updateContentCountry();
            } else {
                $("#requestError").text(data.result);
            }
        },
        error: function() {
            $("#requestError").text("Invalid Request");
        }
    });
}

function logout() {
    //post
    $.post("/logout", $( "#loginForm" ).serialize(), function( data ) {
        if(data.status == "Success") {
            $("#username").text("");
            $("#head").hide();
            $("#content").hide();
            $(".admin").hide();
            $("#login_block").show(200);
        } else{
            $("#loginError").text(data.result);
        }
    })
        .fail(function() {
            $("#loginError").text("Invalid Request");
        })
}

function checkForLoggedIn() {
    $.get("/api/user/login", $( "#loginForm" ).serialize(), function( data ) {
        if(data.status == "Success") {
            loginSuccessFunction(data);
        } else {
            $("#login_block").show();
            $("#loginError").text(data.result);
        }
    })
        .fail(function() {
            $("#login_block").show();
            $("#loginError").text("Invalid Request");
        })
}

function registration(){
    clearMessages();
    //post
    $.post("/api/user", $( "#regForm" ).serialize(), function( data ) {
        if(data.status == "Success") {
            $("#regForm").hide();
            alert("Successfully, Please login now");
        } else{
            $("#regError").text(data.result);
        }
    })
        .fail(function() {
            $("#regError").text("Invalid Request");
        })
}

function login() {
    clearMessages();
    //post
    $.post("/login", $( "#loginForm" ).serialize(), function( data ) {
        if(data.status == "Success") {
            loginSuccessFunction(data);
            $('#loginForm').trigger("reset");
            $("#regForm").hide();
            $("#regForm").trigger("reset");
        } else{
            $("#loginError").text(data.result);
        }
    })
        .fail(function() {
            $("#loginError").text("Invalid Request");
        })
}

function loginSuccessFunction(data) {
    $("#username").text(data.result.username);
    $("#head").show();

    //admin options
    if(data.result.isAdmin) {
        $(".admin").show();
    }
    updateContentCountry();
    $("#content").show();
    $("#login_block").hide(200);
}