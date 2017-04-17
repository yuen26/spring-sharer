$(document).ready(function() {

	$('.progress').hide();
	$('#progress-text').hide();

	$('#upload-form').submit(function(e) {
		e.preventDefault();

		$.ajax({
			url: '/upload',
			type: 'POST',
			data: new FormData($(this)[0]),
			enctype: 'multipart/form-data',
			processData: false, // tell jQuery not to process the data
			contentType: false, // tell jQuery not to set contentType
			cache: false,
			
			beforeSend: function() {
				$('.progress').show();
				$('.progress-bar').width('0%');
				$('#progress-text').show();
				$('#progress-text').html('Bắt đầu tải ...');
				$('.progress-bar').addClass('active');
			},
			
			xhr: function() {
				var xhr = new window.XMLHttpRequest();

				xhr.upload.addEventListener('progress', function(evt) {
					if (evt.lengthComputable) {
						var percentComplete = evt.loaded / evt.total;
						percentComplete = parseInt(percentComplete * 100);
						$('.progress-bar').width(percentComplete + '%');
						$('#progress-text').html('Đang tải lên ...');
					}
				}, false);
				
				return xhr;
			},
			
			success: function(res) {
				console.log(res);
				$('.progress-bar').removeClass('active progress-bar-striped');
				$('#progress-text').html("<i class='fa fa-check-circle-o'></i> File của bạn đã tải lên thành công!");
			},
			
			error: function(res) {
				console.log('ERR: ' + res);
				alert('File của bạn vượt quá dung lượng 25Mb!');
				$('.progress').hide();
				$('#progress-text').hide();
			}
		});
	});

});
