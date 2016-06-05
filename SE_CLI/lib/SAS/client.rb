require 'highline'
require 'time'
require_relative 'communication'

class Client
  def initialize(base_url)
    @cli = HighLine.new
    @proxy = CommunicationProxy.new(base_url)
    @token = nil
  end

  def success?(response)
    response["result"] == "Success"
  end

  def require_login
    if @token.nil?
      @cli.say "Please Login First"
      return true
    else
      return false
    end
  end

  def regist
    username = @cli.ask("Username: ")
    password = @cli.ask("Password: ") { |q| q.echo = false }
    response = @proxy.regist(username, password)
    if success?(response)
      @cli.say("Regist Success")
    else
      @cli.say("Regist Failed")
    end
  end

  def login
    username = @cli.ask("Username: ")
    password = @cli.ask("Password: ") { |q| q.echo = false }
    response = @proxy.login(username, password)
    if success?(response)
      @token = response["token"]
      @cli.say("Login Success")
    else
      @cli.say("Login Failed")
    end
  end

  def add_activity
    return if require_login
    activity_content = @cli.ask("Activity Content: ")
    response = @proxy.add_activity(@token, activity_content)
    if success?(response)
      @cli.say("Activity Added")
    else
      @cli.say("Failed to Add Activity")
    end
  end

  def get_all_activities
    response = @proxy.get_activities
    if success?(response)
      response["activities"].each do |activity|
        @cli.say "#{activity["id"]} - #{activity["content"]}"
      end
    else
      @cli.say("Failed to Fetch Activities")
    end
  end

  def get_my_activities
    return if require_login
    response = @proxy.get_activities(@token)
    if success?(response)
      response["activities"].each do |activity|
        @cli.say "#{activity["id"]} - #{activity["content"]}"
      end
    else
      @cli.say("Failed to Fetch Activities")
    end
  end

  def update_activity
    return if require_login
    id = @cli.ask("Activity ID: ")
    new_content = @cli.ask("New Activity Content: ")
    response = @proxy.update_activity(id, new_content, @token)
    if success?(response)
      @cli.say("Update Successed")
    else
      @cli.say("Update Failed")
    end
  end

  def delete_activity
    return if require_login
    id = @cli.ask("Activity ID: ")
    response = @proxy.delete_activity(id, @token)
    if success?(response)
      @cli.say "Delete Successed"
    else
      @cli.say "Delete Failed"
    end
  end

  def participate
    return if require_login
    id = @cli.ask("Activity ID: ")
    response = @proxy.participate(id, @token)
    if success?(response)
      @cli.say "Participate Successed"
    else
      @cli.say "Participate Failed"
    end
  end

  def add_notice
    return if require_login
    activity_id = @cli.ask("Activity ID: ")
    content = @cli.ask("Notice Content: ")
    time = @cli.ask("Notice Push Time: ", Time)
    response = @proxy.add_notice(@token, content, time, activity_id)
    if success?(response)
      @cli.say "Add Notice Successed"
    else
      @cli.say "Add Notice Failed"
    end
  end

  def get_notices
    return if require_login
    response = @proxy.get_notices(@token)
    if success?(response)
      response["notices"].each do |notice|
        @cli.say "#{notice["content"]} - #{notice["time"]}"
      end
    else
      @cli.say "Fetch notices Failed"
    end
  end

  def update_notice
    return if require_login
    notice_id = @cli.ask("Notice ID: ")
    content = @cli.ask("Notice Content: ")
    time = @cli.ask("Notice Push Time: ", Time)
    response = @proxy.update_notice(@token, content, time, notice_id)
    if success?(response)
      @cli.say "Update Notice Successed"
    else
      @cli.say "Update Notice Failed"
    end
  end

  def delete_notice
    return if require_login
    notice_id = @cli.ask("Notice ID: ")
    response = @proxy.delete_notice(@token, notice_id)
    if success?(response)
      @cli.say "Delete Notice Successed"
    else
      @cli.say "Delete Notice Failed"
    end
  end

  def add_question
    return if require_login
    content = @cli.ask("Question: ")
    response = @proxy.add_question(@token, content)
    if success?(response)
      @cli.say "Add Question Successed"
    else
      @cli.say "Add Question Failed"
    end
  end

  def get_all_questions
    response = @proxy.get_questions
    if success?(response)
      response["questions"].each do |question|
        @cli.say "#{question["id"]} - #{question["content"]}"
      end
    else
      @cli.say "Get Questions Failed"
    end
  end

  def get_my_questions
    return if require_login
    response = @proxy.get_questions(@token)
    if success?(response)
      response["questions"].each do |question|
        @cli.say "#{question["id"]} - #{question["content"]}"
      end
    else
      @cli.say "Get Questions Failed"
    end
  end

  def update_question
    return if require_login
    question_id = @cli.ask("Question ID: ")
    new_content = @cli.ask("New Question Content: ")
    response = @proxy.update_question(@token, new_content, question_id)
    if success?(response)
      @cli.say "Update Question Successed"
    else
      @cli.say "Update Question Failed"
    end
  end

  def delete_question
    return if require_login
    question_id = @cli.ask("Question ID: ")
    response = @proxy.delete_question(@token, question_id)
    if success?(response)
      @cli.say "Delete Question Successed"
    else
      @cli.say "Delete Question Failed"
    end
  end

  def add_answer
    return if require_login
    question_id = @cli.ask("Question ID: ")
    answer_content = @cli.ask("Answer: ")
    response = @proxy.add_answer(@token, answer_content, question_id)
    if success?(response)
      @cli.say "Add Answer Successed"
    else
      @cli.say "Add Answer Failed"
    end
  end

  def get_answers
    question_id = @cli.ask("Question ID: ")
    response = @proxy.get_answers(question_id)
    if success?(response)
      response["answers"].each do |answer|
        @cli.say "#{answer["id"]} - #{answer["content"]}"
      end
    else
      @cli.say "Fetch answers Failed"
    end
  end

  def update_answer
    return if require_login
    answer_id = @cli.ask("Answer ID: ")
    new_content = @cli.ask("New Answer Content: ")
    response = @proxy.update_answer(@token, new_content, answer_id)
    if success?(response)
      @cli.say "Update Answer Successed"
    else
      @cli.say "Update Answer Failed"
    end
  end

  def delete_answer
    return if require_login
    answer_id = @cli.ask("Answer ID: ")
    response = @proxy.delete_answer(@token, answer_id)
    if success?(response)
      @cli.say "Delete Answer Successed"
    else
      @cli.say "Delete Answer Failed"
    end
  end
end
