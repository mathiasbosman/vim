import http from "../http-common";

class CategoryDataService {
  getAll() {
    return http.get("/categories");
  }
}

export default new CategoryDataService();