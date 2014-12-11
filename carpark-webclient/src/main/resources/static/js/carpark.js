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
    $(".alert-dismissable").fadeTo(500, 0).slideUp(500, function(){
        $(".alert-dismissable").alert('close');
    });
},30000);

function ajaxLoader (el, options) {
	// Becomes this.options
	var defaults = {
		bgColor 		: '#fff',
		duration		: 800,
		opacity			: 0.7,
		classOveride 	: false
	}
	this.options 	= jQuery.extend(defaults, options);
	this.container 	= $(el);
	
	this.init = function() {
		var container = this.container;
		// Delete any other loaders
		this.remove(); 
		// Create the overlay 
		var overlay = $('<div></div>').css({
				'background-color': this.options.bgColor,
				'opacity':this.options.opacity,
				'width':container.width(),
				'height':container.height(),
				'position':'absolute',
				'top':'0px',
				'left':'0px',
				'z-index':99999
		}).addClass('ajax_overlay');
		// add an overiding class name to set new loader style 
		if (this.options.classOveride) {
			overlay.addClass(this.options.classOveride);
		}
		// insert overlay and loader into DOM 
		container.append(
			overlay.append(
				$('<div></div>').addClass('ajax_loader')
			).fadeIn(this.options.duration)
		);
    };
	
	this.remove = function(){
		var overlay = this.container.children(".ajax_overlay");
		if (overlay.length) {
			overlay.fadeOut(this.options.classOveride, function() {
				overlay.remove();
			});
		}	
	}

    this.init();
}	

var options = {
	bgColor 		: '#000',
	duration		: 800,
	classOveride 	: false
};

var getSpinner = function()
{
    return new ajaxLoader("body", options);
};

