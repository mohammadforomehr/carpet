<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreatePostsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('posts', function (Blueprint $table) {
            $table->increments('id');
            $table->string('title',50);
            $table->text('caption')->nullable();
            $table->integer('code_carpet')->nullable();
            $table->string('color');
            $table->string('Plan');
            $table->string('size');
            $table->integer('shoulder');
            $table->integer('price');
            $table->integer('id_category');
            $table->string('image');
            $table->string('amazing')->default('false');
            $table->string('status')->default('true');
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('posts');
    }
}
