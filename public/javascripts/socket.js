$(function(){
			var socket= io.connect('http://localhost:3000');
			var $messageForm=$('#messageForm');
			var $message=$('#message');
			var $chat=$('#chatWindow');
			
			
			$messageForm.submit(function(e){
				e.preventDefault();
				socket.emit('send message',$message.val());
				$message.val('');
			});

			socket.on('new message',function(data){
				$chat.append(data.msg+'<br>');
			});

			

		});