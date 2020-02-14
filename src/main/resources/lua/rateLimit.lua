local key = KEYS[1]
local limit = tonumber(ARGV[1])
local time = ARGV[2]


local res = redis.call('incr', key)

if res == 1 then
    redis.call('expire', key, time)
end

if res > limit then
    return 0
end
return 1