<?php
namespace TDD\Test;

require dirname(dirname((__FILE__))) . DIRECTORY_SEPARATOR . 'vendor' . DIRECTORY_SEPARATOR . 'autoload.php';


use PHPUnit\Framework\TestCase;
// use PHPUnit\Framework\PHPUnit_Framework_Error_Warning;



class TodoTest extends TestCase{

	public function setUp(){
	
	}

	public function tearDown(){
		
	}


	public function testloadTODO(){ 

		$todo_lists = loadTODO();
		$this->assertInternalType('array',$todo_lists);
		
	}

	public function testloadCategories(){ 

		$todo_lists = loadCategories();
		$this->assertInternalType('array',$todo_lists);

	}


	/**
	*  @dataProvider provideTime
	**/
	public function testtimeSince($seconds_passed, $time_ago){ 

		require_once 'include.inc';
		$output = timeSince($seconds_passed);
		$this->assertEquals(
			$time_ago,
			$output,
			'The time since calculated does\'t match the provided'
		);
	}


	public function provideTime(){
		// time() - 30 means 30 seconds from now
		// and 30 seconds from now must return 0 minute ago
		// similaryly
		// time() - 60 must return 1 minute ago 
		// time() - 65 must return 1 minute ago
		return [
			[time(),'0 minute ago'],
			[time() - 30,'0 minute ago'],
			[time() - 59,'0 minute ago'],
			[time() - 60,'1 minute ago'],
			[time() - 65,'1 minute ago'],
			
		];
	}











}


?>