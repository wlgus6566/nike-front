import { contents } from './index';

// posts
function createNewPost(data) {
    return contents.post('/', data);
}

function fetchPosts() {
    return contents.get('/');
}

function fetchPostById(id) {
    return contents.get(id);
}

function editPostById(id, data) {
    return contents.put(id, data);
}

function deletePostById(id) {
    return contents.delete(id);
}

export { createNewPost, fetchPosts, fetchPostById, editPostById, deletePostById };
