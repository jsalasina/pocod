var cellsOpen = 0; //cells counter
let status = 1;    //game status
let ajax;

//when player starts the game
$('body').on('click', '#start-game', function(){
    status = 1;
    cellsOpen = 0;
    $('p').css('display', 'none');
    //sending request to server
    $.ajax({
        url : 'game',
        type: 'get',
        data : {
        },
        success:function(){
            // when the response is got, draw the playing field
            $('div').empty();
            var br = '<br>';
            $('div').append(br);
            for(var i = 0; i< 10; i++){
                for(var j = 0; j<10; j++){
                    $('div').append('<button class="cell" id="' + String(i*10 + j+1) +'" style = "width: 50px; height: 50px; "></button>');
                }
                var br = '<br>';
                $('div').append(br);
            }
        },
    });
});

// left mouse button handler
$('body').on('mousedown', '.cell', function(e){

    if( e.button == 2 ) {
        var id = $(this).closest('.cell').attr('id');
        $.ajax({
            url: 'flag',
            type: 'post',
            data: {
                id: id
            },
            success: function (resp) {
                if(resp=="true"){
                    // set flag
                    $('#' +id).addClass('flag');
                }
                // remove flag
                else $('#' +id).removeClass('flag');
            },
        });
    }
});

// open cells: right mouse button handler
$('body').on('click', '.cell', function(){
        // if cells aren't opening right now
        if(!ajax) {
            var cellId = $(this).closest('.cell').attr('id');
            // if the flag isn't set, open cell
            if (!$('#' + cellId).hasClass('flag')) {
                ajax = $.ajax({
                    url: 'cellopen',
                    type: 'POST',
                    data: {
                        id: cellId,
                        cellsOpen: cellsOpen,
                    },
                    success: function (responseJson) {
                        $.each(responseJson, function (index, cell) {
                            switch (cell.value) {
                                case -1: {
                                    $('#' + cell.id).removeClass('flag');
                                    // for exploded bomb
                                    if (cell.id == cellId) {
                                        $('#' + cellId).css("background-image", "url(img/explosion.jpg)");
                                    }
                                    // for other bombs
                                    else $('#' + cell.id).css("background-image", "url(img/bomb-explosion.png)");
                                    //disable any opened cell
                                    $('#' + cell.id).prop('disabled', true);
                                    status = 0;
                                }
                                break;
                                case 0: {
                                    $('#' + cell.id).css('background-color', 'rgb(128, 128, 128)');
                                    $('#' + cell.id).prop('disabled', true);
                                    cellsOpen++;
                                }
                                break;
                                case 1: {
                                    $('#' + cell.id).css("background-image", "url(img/one1.png)");
                                    $('#' + cell.id).prop('disabled', true);
                                    cellsOpen++;
                                }
                                break;
                                case 2: {
                                    $('#' + cell.id).css("background-image", "url(img/two.png)");
                                    $('#' + cell.id).prop('disabled', true);
                                    cellsOpen++;
                                }
                                break;
                                case 3: {
                                    $('#' + cell.id).css("background-image", "url(img/three.png)");
                                    $('#' + cell.id).prop('disabled', true);
                                    cellsOpen++;
                                }
                                break;
                                case 4: {
                                    $('#' + cell.id).css("background-image", "url(img/four.png)");
                                    $('#' + cell.id).prop('disabled', true);
                                    cellsOpen++;
                                }
                                break;
                                case 5: {
                                    $('#' + cell.id).css("background-image", "url(img/five.png)");
                                    $('#' + cell.id).prop('disabled', true);
                                    cellsOpen++;
                                }
                                break;
                                case 6: {
                                    $('#' + cell.id).css("background-image", "url(img/six.png)");
                                    $('#' + cell.id).prop('disabled', true);
                                    cellsOpen++;
                                }
                                break;
                                case 7: {
                                    $('#' + cell.id).css("background-image", "url(img/seven.png)");
                                    $('#' + cell.id).prop('disabled', true);
                                    cellsOpen++;
                                }
                                break;
                                case 8: {
                                    $('#' + cell.id).css("background-image", "url(img/eight.png)");
                                    $('#' + cell.id).prop('disabled', true);
                                    cellsOpen++;
                                }
                                break;
                            }

                        })
                        // if bomb reached, end game
                        if (!status) {
                            $('.cell').prop('disabled', true);
                        }
                        // if all clear cells opened
                        if (status && cellsOpen == 90) {
                            // end game like a winner
                            $('p').css('display', 'inline');
                            $('.cell').prop('disabled', true);
                        }
                        ;
                        // clear ajax
                        ajax = null;
                    },
                });
            }
        }
});

// reject contextmenu
$('div').bind('contextmenu', function (e) {
    return false;
});