package controllers

import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.libs.json._
import play.api.db._
import anorm._
import play.api.Play.current

object Application extends Controller {
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  // @params    bssids[] bssid
  // @response  
  def get_venue(bssids_json: String) = Action {
    val bssid_list = Json.parse(bssids_json).as[List[String]]
    var response: List[ Map[String, String]] = null
    
    DB.withConnection { implicit c =>
      val selectBssid = SQL("SELECT * FROM bssids WHERE id = {id}").on("id" -> "test_id")
      selectBssid().map(row => 
        response = List.concat (response, List( Map(row[String]("id") -> row[String]("name"))))
      )
    }
    
    Ok(Json.toJson(response))
  }
  
  def post_venue(bssids: String) = Action {
    DB.withConnection { implicit c =>
      // BSSID 登録
      SQL("INSERT INTO bssids (id, name) VALUES ('test_id', 'test_name')").execute()
    }
    
    Ok(Json.toJson(true))
  }
  
}
