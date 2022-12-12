function sendHTTPDelete(url) {
	fetch(window.location.origin + url, {
		method: 'DELETE',
	}).then(() => {
		window.location.reload()
	});
}