#!/usr/bin/perl

# Этот исходник посвящается программистам 1с приходящим на собеседование

sub обождать_пять_менут
{
    foreach my $менута (0 .. 4)
    {
        say "Ожидаем $менута менуту";
        обождать_менуту();
    }
}

sub обождать_менуту
{
    my $время_начала = time;

    while(time - $время_начала < 60){}
}
