var base = "http://162.243.27.156/";

window.addEventListener("load", function(){
    $(".button").on("click", function(){
        var open = $(".button a").text();
        console.log(open);

        if(open == "open")
            $(".button a").text("close");
        else
            $(".button a").text("open");

        $.ajax({
            type: "POST",
            url : base,
            data : {"json" : "{\"stat\" : \"" + open.toLowerCase() + "\"}"}
        }).done(function(data){
            console.log(data);
        });
    });
    $(".button").on("mouseover", function(){
        $(".button a").css("background", "#08C");
    });
    $(".button").on("mouseout", function(){
        $(".button a").css("background", "5f5f5f");
    });
    $(".button").on("active", function(){
        
    });


    var check = function(){
    $.ajax({
        type : "GET",
        url : base + "getStat",
    }).done(function(data){

        if(data == "close")
            $(".stat").text("closed").css("color","red");
        else
            $(".stat").text("opened").css("color","green");;
        
        data = (data == "open") ? "close" : "open";

        $(".button a").text(data);
    });
    };

    setInterval(check, 1000);
});
