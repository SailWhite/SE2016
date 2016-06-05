require "uri"
require "json"
require "net/http"

class CommunicationProxy
  def initialize(base_url)
    @uri = URI.parse(base_url)
  end

  def regist(username, password)
    get({
          "command" => "regist",
          "username" => username,
          "password" => password
        })
  end

  def login(username, password)
    get({
          "command" => "login",
          "username" => username,
          "password" => password
        })
  end

  def add_activity(token, content)
    get({
          "command" => "addActivity",
          "token" => token,
          "content" => content
        })
  end

  def get_activities(token = nil)
    data = {
      "command" => "getActivities"
    }
    data["token"] = token if token
    get(data)
  end

  def update_activity(id, content, token)
    get({
          "command" => "updateActivity",
          "id" => id,
          "content" => content,
          "token" => token
        })
  end

  def delete_activity(id, token)
    get({
          "command" => "deleteActivity",
          "id" => id,
          "token" => token
        })
  end

  def participate(id, token)
    get({
          "command" => "participate",
          "id" => id,
          "token" => token
        })
  end

  def add_notice(token, content, time, activity_id)
    get({
          "command" => "addNotice",
          "token" => token,
          "content" => content,
          "time" => time,
          "id" => activity_id
        })
  end

  def get_notices(token)
    get({
          "command" => "getNotices",
          "token" => token
        })
  end

  def update_notice(token, content, time, id)
    get({
          "command" => "updateNotice",
          "token" => token,
          "content" => content,
          "time" => time,
          "id" => id
        })
  end

  def delete_notice(token, id)
    get({
          "command" => "deleteNotice",
          "token" => token,
          "id" => id
        })
  end

  def add_question(token, content)
    get({
          "command" => "addQuestion",
          "token" => token,
          "content" => content
        })
  end

  def get_questions(token = nil)
    data = {
      "command" => "getQuestions"
    }
    data["token"] = token if token
    get(data)
  end

  def update_question(token, content, id)
    get({
          "command" => "updateQuestion",
          "token" => token,
          "content" => content,
          "id" => id
        })
  end

  def delete_question(token, id)
    get({
          "command" => "deleteQuestion",
          "token" => token,
          "id" => id
        })
  end

  def add_answer(token, content, questions_id)
    get({
          "command" => "addAnswer",
          "token" => token,
          "content" => content,
          "id" => questions_id
        })
  end

  def get_answers(question_id)
    get({
          "command" => "getAnswers",
          "id" => question_id
        })
  end

  def update_answer(token, content, id)
    get({
          "command" => "updateAnswer",
          "token" => token,
          "content" => content,
          "id" => id
        })
  end

  def delete_answer(token, id)
    get({
          "command" => "deleteAnswer",
          "token" => token,
          "id" => id
        })
  end

  private

  def get(request_data)
    params = {
      "json" => request_data
    }
    @uri.query = URI.encode_www_form(params)
    response = Net::HTTP.get(@uri)

    # Hack: clean up response json
    response.gsub!('\\', '')
    response.gsub!('"{', '{')
    response.gsub!('}"', '}')

    JSON.parse(response)
  end
end
