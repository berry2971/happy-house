import axios from "axios";

function apiInstance() {
  const instance = axios.create({
    baseURL: "http://localhost:8090",
    headers: {
      "Content-type": "application/json",
    },
  });
  return instance;
}

async function login(user, success, fail) {
  await apiInstance().post(`/users/auth/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(id, success, fail) {
  await apiInstance()
    .get(`/users/${id}`, {
      headers: {
        Authorization: `Bearer ` + sessionStorage.getItem("token"),
      },
    })
    .then(success)
    .catch(fail);
}

export { login, findById };
