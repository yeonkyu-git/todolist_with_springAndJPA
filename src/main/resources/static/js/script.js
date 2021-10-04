window.onload = function () {

};


function enrollTodo() {
    var categoryId = $('#categoryId').val();
    var todoName = $('#todo').val();
    var deadline = $('#datePicker').val();


    $.ajax({
        url: "/todolist/create",
        data: {categoryId, todoName, deadline},
        type: "POST",
    }).done(function (fragment) {
        $('#sample').replaceWith(fragment)
    })
}

function deleteTodo(a) {
    var todoId = $(a).attr('data-id');

    $.ajax({
        url: "/todolist/delete",
        data: {todoId},
        type: "POST",
    }).done(function (fragment) {
              $('#sample').replaceWith(fragment)
          })
}

function complete(a) {
    var todoId = $(a).attr('data-id');

    $.ajax({
        url: "/todolist/complete",
        data: {todoId},
        type: "POST",
    }).done(function (fragment) {
              $('#sample').replaceWith(fragment)
          })
}



//    var data=$("#input").val();
//
//    var messageDTO={
//        result:data
//    };
//
//    $.ajax({
//        url: "/dataSend",
//        data: messageDTO,
//        type:"POST",
//    }).done(function (fragment) {
//        $("#resultDiv").replaceWith(fragment);
//    });

