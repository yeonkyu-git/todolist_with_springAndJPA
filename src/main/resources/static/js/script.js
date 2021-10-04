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

function updateTodo(a) {
    var todoId = $(a).attr('data-id');
    $("#todoInputReadOnly"+todoId).addClass("d-none")
    $("#todoInputWrite"+todoId).removeClass("d-none")
    $("#todoInputButton"+todoId).removeClass("d-none")
    $("#todoInputWrite"+todoId).focus();

}

function updateTitleTodo(a){
        var todoId = $(a).attr('data-id');
        var title = $("#todoInputWrite"+todoId).val();

        $.ajax({
            url: "/todolist/update",
            data: {todoId, title},
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

function deleteCategory(a) {
    var categoryId = $(a).attr('data-id');
    console.log(categoryId);

    $.ajax({
        url: "/category/delete",
        data: {categoryId},
        type: "POST",
        success: function(data, txtStatus) {
                window.location.href = "http://localhost:8080/todolist"
            }
    })
}