<?php
namespace TDD\Test;

require dirname(dirname((__FILE__))) . DIRECTORY_SEPARATOR . 'vendor' . DIRECTORY_SEPARATOR . 'autoload.php';


use PHPUnit\Framework\TestCase;
use GuzzleHttp\Client;

// composer require guzzlehttp/guzzle

class APITest extends TestCase{

	private $httpClient;

	public function setUp(){
		 $this->httpClient = new Client(['base_uri' => 'http://localhost/nss-todo-automation/fake-API-call.php']);
		 // base uri should be http://localhost/nss-todo-automation/fake-API-call.php
	}

	public function tearDown(){
		unset($this->httpClient);
	}


	public function testEntryEndpoint(){
		// check the status code
		 $response = $this->httpClient->request('GET', '');
		$this->assertEquals(200, $response->getStatusCode(),"Entry API Endpoint must return 0k status code (200) and some data if there exists");


		// cheeck the json structure
		$data = json_decode($response->getBody(), true); // returns the whole json data

		$data2 = $data[0];// retrive just the first entry in the json and check the structure of the json
		
		$this->assertArrayHasKey('id', $data2);
		$this->assertArrayHasKey('status', $data2);
		$this->assertArrayHasKey('task name', $data2);
		$this->assertArrayHasKey('category', $data2);
		$this->assertArrayHasKey('due date', $data2);
	}


	public function testCategoryAssigned(){ // Find how many tasks do not have "category" assigned
		 $response = $this->httpClient->request('GET', '');
		 $todo_datas = json_decode($response->getBody(), true);
		 $todos_without_category = 0;
		 foreach($todo_datas as $todo){
		 	if($todo['category'] == "")
		 		$todos_without_category += 1;
		 }
		 $this->assertEquals(6,$todos_without_category,"todos without category are {$todos_without_category}");
	}

	public function testTaskNames(){ // Aggregate and print only "task names"
		$response = $this->httpClient->request('GET', '');
		$todo_datas = json_decode($response->getBody(), true);

		$counter = 1;
		foreach($todo_datas as $todo){
			print("\nTask {$counter} : " . $todo['task name']);
			$counter++;
		}
	}

	public function testTaskDateOrder(){ // Read API response and print tasks in descending "due date" order
		// the api returns tasks in ascending order ( from past to most recent)
		$total_tasks = 10;
		$response = $this->httpClient->request('GET', '');
		$todo_datas = json_decode($response->getBody(), true);

		foreach($todo_datas as $todo){
			var_dump($todo);
		}
	}

	public function testValidateTasks(){ // Count and validate the number of tasks
		$total_tasks = 10;
		$response = $this->httpClient->request('GET', '');
		$todo_datas = json_decode($response->getBody(), true);

		$total_tasks_counted = count($todo_datas);

		$this->assertEquals($total_tasks,$total_tasks_counted,"Total added tasks initially is 10 and the api shoud return 10 tasks");

	}

}


?>