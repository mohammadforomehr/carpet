<?php

use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/


//product
Route::get('/app/new',\App\Http\Controllers\PostController::class . '@news');
Route::get('/app/amazing',\App\Http\Controllers\PostController::class . '@amazing');
Route::get('/app/detail/{id}',\App\Http\Controllers\PostController::class . '@detail')->whereNumber('id');
Route::get('/app/related/{id}',\App\Http\Controllers\PostController::class . '@related')->whereNumber('id');
//search
Route::get('/app/search/{value}',\App\Http\Controllers\PostController::class . '@search');
//category
Route::get('/app/cat_parent',\App\Http\Controllers\app\CategoryController::class . '@parent');
Route::get('/app/cat_child/{parent_id}',\App\Http\Controllers\app\CategoryController::class . '@child')->whereNumber('parent_id');
Route::get('/app/cat_carpet/{id_category}',\App\Http\Controllers\app\CategoryController::class . '@category_carpet')->whereNumber('id_category');
//offers
Route::post('/app/offers',\App\Http\Controllers\app\OffersController::class .'@store');
//news
Route::get('/app/news',\App\Http\Controllers\app\NewsController::class . '@index');
//benner
Route::get('/app/baner',\App\Http\Controllers\app\BanerController::class . '@index');
//about
Route::get('/app/about',\App\Http\Controllers\app\AboutController::class . '@index');