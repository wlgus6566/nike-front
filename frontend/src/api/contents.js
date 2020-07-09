import { contents } from './index';

// posts
function createContents(data) {
    return contents.post('/', data);
}

function fetchContents() {
    return contents.get('/');
}

function fetchContentsById(id) {
    return contents.get(id);
}

function editContentsById(id, data) {
    return contents.put(id, data);
}

function deleteContentsById(id) {
    return contents.delete(id);
}

export { createContents, fetchContents, fetchContentsById, editContentsById, deleteContentsById };
