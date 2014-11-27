$('button[name = "delete"]').on('click', function (e) {
    var $form = $(this).closest('form');
    e.preventDefault();
    $('#confirm-delete').modal({backdrop: 'static'})
            .one('click', '#confirm-btn', function (e) {
                $form.trigger('submit');
            });
});

$('#carDetails').on('show.bs.modal', function (event) {
    event.preventDefault();
    
    var button = $(event.relatedTarget);
    var id = button.data('car-id');
    var brand = button.data('car-brand');
    var type = button.data('car-type');
    var color = button.data('car-color');
    var engine = button.data('car-engine');
    var model = button.data('car-model');
    var licencePlate = button.data('car-licencePlate');
    var vin = button.data('car-vin');

    var modal = $(this);
    modal.find('.modal-body input[name = "id"]').val(id);
    modal.find('.modal-body input[name = "brand"]').val(brand);
    modal.find('.modal-body input[name = "type"]').val(type);
    modal.find('.modal-body input[name = "color"]').val(color);
    modal.find('.modal-body input[name = "engine"]').val(engine);
    modal.find('.modal-body input[name = "model"]').val(model);
    modal.find('.modal-body input[name = "licencePlate"]').val(licencePlate);
    modal.find('.modal-body input[name = "vin"]').val(vin);
});

window.setTimeout(function() {
    $(".alert-dismissable").fadeTo(500, 0).slideUp(500, function(){
        $(".alert-dismissable").alert('close');
    });
},5000);
