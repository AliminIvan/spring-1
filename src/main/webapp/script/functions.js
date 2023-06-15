function delete_task(task_id) {
    let url = "/" + task_id;
    $.ajax({
        url: url,
        type: 'DELETE',
        success: function () {
            window.location.reload();
        }
    });
}

function edit_task(task_id) {
    let delete_button = "#delete_" + task_id;
    $(delete_button).remove();

    let edit_button = "#edit_" + task_id;
    let save_button = "<button id='save_" + task_id + "'>Save</button>";
    $(edit_button).html(save_button);
    let property_save_button = "update_task(" + task_id + ")";
    $(edit_button).attr("onclick", property_save_button);

    let current_tr_element = $(edit_button).parent().parent();
    let children = $(current_tr_element).children();
    let td_description = children[1];
    td_description.innerHTML = "<input id='input_description_" + task_id + "' type='text' value='" + td_description.innerHTML + "'>";

    let td_status = children[2];
    let status_id = "#select_status_" + task_id;
    let status_current_value = td_status.innerHTML;
    td_status.innerHTML = getDropDownStatusHtml(task_id);
    $(status_id).val(status_current_value).change();
}


function getDropDownStatusHtml(task_id) {
    let status_id = "select_status_" + task_id;
    return "<label for='status'></label>"
        +"<select id=" + status_id + " name='status'>"
        +"<option value='IN_PROGRESS'>IN_PROGRESS</option>"
        +"<option value='DONE'>DONE</option>"
        +"<option value='PAUSED'>PAUSED</option>"
        +"</select>";
}

function update_task(task_id) {
    let url = "/" + task_id;
    let value_description = $("#input_description_" + task_id).val();
    let value_status = $("#select_status_" + task_id).val();

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        async: false,
        data: JSON.stringify({"description": value_description, "status": value_status})
    });

    setTimeout(() => {
        document.location.reload();
    }, 300)
}

function add_task() {
    let value_description = $("#description_new").val();
    let value_status = $("#status_new").val();

    $.ajax({
        url: "/",
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        async: false,
        data: JSON.stringify({"description": value_description, "status": value_status})
    });

    setTimeout(() => {
        document.location.reload();
    }, 300)
}