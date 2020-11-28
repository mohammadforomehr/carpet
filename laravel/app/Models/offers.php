<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class offers extends Model
{
    protected $fillable = [
        'name', 'email','phone','addressweb','message',
    ];
    use HasFactory;
}
