function showDeleteModal(id) {
    const modal = document.getElementById('deleteModal');
    const cardHeader = document.getElementById(id + 'header');
    const cardBody = document.getElementById(id + 'body');
    const modalCardHeaderBlock = document.getElementsByClassName('modal-card-header');
    const modalCardBodyBlock = document.getElementsByClassName('modal-card-body');
    const confirmButton = document.getElementById('confirmDelete');
    confirmButton.setAttribute('modal-customer-id', id);
    modalCardHeaderBlock[0].innerHTML = cardHeader.innerHTML;
    modalCardBodyBlock[0].innerHTML = cardBody.innerHTML;
    modal.style.display = 'block';
}

function cancelDeleteModal() {
    const modal = document.getElementById('deleteModal');
    const modalCardHeaderBlock = document.getElementsByClassName('modal-card-header');
    const modalCardBodyBlock = document.getElementsByClassName('modal-card-body');
    const confirmButton = document.getElementById('confirmDelete');
    confirmButton.setAttribute('modal-customer-id', null);
    modalCardHeaderBlock[0].innerHTML = '';
    modalCardBodyBlock[0].innerHTML = '';
    modal.style.display = 'none';
}

function confirmDeleteModal(id) {
    const deleteForm = document.getElementById(id + "form-delete")
    deleteForm.submit();
    cancelDeleteModal();
}