$('button[name = "delete"]').on('click', function (e) {
    var $form = $(this).closest('form');
    e.preventDefault();
    $('#confirm-delete').modal({backdrop: 'static'})
            .one('click', '#confirm-btn', function (e) {
                $form.trigger('submit');
            });
});

$('#carDetails').on('show.bs.modal', function (event) {
    
    var button = $(event.relatedTarget);
    var id = button.data('car-id');
    var brand = button.data('car-brand');
    var type = button.data('car-type');
    var engine = button.data('car-engine');
    var licencePlate = button.data('car-licenceplate');

    var modal = $(this);
    modal.find('a[name = "id"]').text(id);
    
    var href = modal.find('a[name = "id"]').attr("href");
    var editedHref = href.substring(0, href.indexOf("#"));    
    
    modal.find('a[name = "id"]').attr("href", editedHref + "#" + id);
    
    modal.find('p[name = "brand"]').text(brand);
    modal.find('p[name = "type"]').text(type);
    modal.find('.modal-body p[name = "engine"]').text(engine);
    modal.find('.modal-body p[name = "licencePlate"]').text(licencePlate);
});

window.setTimeout(function() {
    if($(".alert-dismissable").css("display") != "none")
    {
        $(".alert-dismissable").fadeTo(500, 0).slideUp(500, function(){
            $(".alert-dismissable").alert('close');
        });
    }
},30000);

