export const getToken = () => {
  return localStorage.getItem("authToken");
};

export const redirectToLogin = () => {
  window.location.href = "/login";
};
